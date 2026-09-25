#!/bin/bash

set -e

# ─────────────────────────────────────────
# CORES
# ─────────────────────────────────────────
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

# ─────────────────────────────────────────
# FUNÇÕES DE LOG
# ─────────────────────────────────────────
log_info()    { echo -e "${BLUE}[INFO]${NC} $1"; }
log_success() { echo -e "${GREEN}[OK]${NC} $1"; }
log_warn()    { echo -e "${YELLOW}[WARN]${NC} $1"; }
log_error()   { echo -e "${RED}[ERRO]${NC} $1"; exit 1; }

# ─────────────────────────────────────────
# USO
# ─────────────────────────────────────────
usage() {
    echo ""
    echo "Uso: ./deploy.sh [dev|prod]"
    echo ""
    echo "  dev   — sobe o ambiente de desenvolvimento"
    echo "  prod  — sobe o ambiente de produção"
    echo ""
    exit 1
}

# ─────────────────────────────────────────
# ARGUMENTO OBRIGATÓRIO
# ─────────────────────────────────────────
[ -z "$1" ] && usage

case "$1" in
    dev)
        ENVIRONMENT="dev"
        COMPOSE_FILE="compose.dev.yaml"
        ENV_FILE=".env.dev"
        CONTAINER_DB="moneyflow-database-dev"
        ;;
    prod)
        ENVIRONMENT="prod"
        COMPOSE_FILE="compose.prod.yaml"
        ENV_FILE=".env.prod"
        CONTAINER_DB="moneyflow-database-prod"
        ;;
    *)
        log_error "Ambiente inválido: '$1'. Use 'dev' ou 'prod'."
        ;;
esac

PROJECT_NAME="moneyflow"

# ─────────────────────────────────────────
# DETECÇÃO DO RUNTIME
# ─────────────────────────────────────────
detect_runtime() {
    if command -v podman &>/dev/null; then
        RUNTIME="podman"
        COMPOSE_CMD="podman compose"
        log_info "Runtime detectado: Podman"
    elif command -v docker &>/dev/null; then
        RUNTIME="docker"
        COMPOSE_CMD="docker compose"
        log_info "Runtime detectado: Docker"
    else
        log_error "Nenhum runtime encontrado. Instale Docker ou Podman."
    fi
}

# ─────────────────────────────────────────
# FUNÇÕES
# ─────────────────────────────────────────
check_requirements() {
    log_info "Verificando dependências..."

    [ -f "$COMPOSE_FILE" ] || log_error "Arquivo $COMPOSE_FILE não encontrado"
    [ -f "$ENV_FILE" ]     || log_error "Arquivo $ENV_FILE não encontrado"

    $COMPOSE_CMD version &>/dev/null || log_error "Compose não encontrado"

    log_success "Dependências OK"
}

check_socket() {
    log_info "Verificando socket do $RUNTIME..."

    if [ "$RUNTIME" = "podman" ]; then
        SOCK_PATH=$(grep DOCKER_SOCK "$ENV_FILE" 2>/dev/null | cut -d '=' -f2)
        SOCK_PATH=${SOCK_PATH:-/run/user/$(id -u)/podman/podman.sock}

        if [ ! -S "$SOCK_PATH" ]; then
            log_warn "Socket não encontrado. Ativando podman.socket..."
            systemctl --user enable --now podman.socket
            sleep 2
        fi

        [ -S "$SOCK_PATH" ] \
            && log_success "Socket OK: $SOCK_PATH" \
            || log_error "Falha ao ativar o socket do Podman"
    else
        if ! $RUNTIME info &>/dev/null; then
            log_warn "Docker daemon não está rodando. Iniciando..."
            sudo systemctl enable --now docker
            sleep 2
        fi

        $RUNTIME info &>/dev/null \
            && log_success "Docker daemon OK" \
            || log_error "Falha ao iniciar o Docker daemon"
    fi
}

check_port() {
    log_info "Verificando porta privilegiada..."

    if [ "$RUNTIME" = "podman" ]; then
        CURRENT=$(sysctl -n net.ipv4.ip_unprivileged_port_start)
        if [ "$CURRENT" -gt 80 ]; then
            log_warn "Porta 80 bloqueada (mínimo atual: $CURRENT). Liberando..."
            sudo sysctl -w net.ipv4.ip_unprivileged_port_start=80
            log_success "Porta 80 liberada"
        else
            log_success "Porta 80 já liberada"
        fi
    else
        log_success "Docker não requer ajuste de porta privilegiada"
    fi
}

create_networks() {
    log_info "Verificando networks..."

    for NETWORK in backend frontend; do
        if ! $RUNTIME network exists "$NETWORK" &>/dev/null; then
            $RUNTIME network create "$NETWORK"
            log_success "Network '$NETWORK' criada"
        else
            log_info "Network '$NETWORK' já existe"
        fi
    done
}

pull_latest() {
    log_info "Atualizando imagens base..."
    $RUNTIME pull docker.io/postgres:latest
    $RUNTIME pull docker.io/jc21/nginx-proxy-manager:latest
    $RUNTIME pull docker.io/linuxserver/duckdns:latest

    if [ "$ENVIRONMENT" = "prod" ]; then
        $RUNTIME pull docker.io/portainer/portainer-ce:latest
    fi

    log_success "Imagens atualizadas"
}

build_images() {
    log_info "Construindo imagens da aplicação..."
    $COMPOSE_CMD -f "$COMPOSE_FILE" --env-file "$ENV_FILE" build --no-cache
    log_success "Build concluído"
}

stop_containers() {
    log_info "Derrubando containers em execução..."
    $COMPOSE_CMD -f "$COMPOSE_FILE" --env-file "$ENV_FILE" down --remove-orphans
    log_success "Containers parados"
}

start_containers() {
    log_info "Subindo containers..."
    $COMPOSE_CMD -f "$COMPOSE_FILE" --env-file "$ENV_FILE" up -d
    log_success "Containers iniciados"
}

wait_healthy() {
    log_info "Aguardando banco de dados ficar saudável..."

    RETRIES=15
    COUNT=0

    until $RUNTIME healthcheck run "$CONTAINER_DB" &>/dev/null; do
        COUNT=$((COUNT + 1))
        if [ "$COUNT" -ge "$RETRIES" ]; then
            log_error "Banco não ficou saudável após ${RETRIES} tentativas"
        fi
        log_warn "Banco ainda não está pronto... ($COUNT/$RETRIES)"
        sleep 5
    done

    log_success "Banco de dados pronto"
}

cleanup() {
    log_info "Limpando imagens antigas..."
    $RUNTIME image prune -f
    log_success "Limpeza concluída"
}

show_status() {
    echo ""
    echo -e "${BLUE}══════════════════════════════════════${NC}"
    echo -e "${BLUE}   STATUS — $PROJECT_NAME ($ENVIRONMENT)  ${NC}"
    echo -e "${BLUE}══════════════════════════════════════${NC}"
    $COMPOSE_CMD -f "$COMPOSE_FILE" ps
    echo ""
}

# ─────────────────────────────────────────
# EXECUÇÃO
# ─────────────────────────────────────────
echo ""
echo -e "${GREEN}══════════════════════════════════════${NC}"
echo -e "${GREEN}  DEPLOY $PROJECT_NAME — $ENVIRONMENT ${NC}"
echo -e "${GREEN}══════════════════════════════════════${NC}"
echo ""

detect_runtime
check_requirements
check_socket
check_port
create_networks
stop_containers
pull_latest
build_images
start_containers
wait_healthy
cleanup
show_status

echo -e "${GREEN}Deploy $ENVIRONMENT finalizado com sucesso! ($RUNTIME)${NC}"
echo ""