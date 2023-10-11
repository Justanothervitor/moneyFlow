#Imagem básica do mongodb
FROM mongo:5.0.13
#Autor
MAINTAINER Joel Rodrigues Moreira
#Nome de usuário do banco de dados
ARG DATABASE_USERNAME
#Senha do usuário do banco de dados
ARG DATABASE_PASSWORD
#Nome do banco de dados
ARG DATABASE_NAME
#Usuário de acesso ao banco
ENV MONGO_INITDB_ROOT_USERNAME=$DATABASE_USERNAME
#Senha de acesso ao banco
ENV MONGO_INITDB_ROOT_PASSWORD=$DATABASE_PASSWORD
#Nome do Schema
ENV MONGO_INITDB_DATABASE=$DATABASE_NAME
#Criando o arquivo de script de configuração do banco
RUN touch /docker-entrypoint-initdb.d/initializedb.js
#Adicionado o conteudo do script de configuração
RUN echo "\
        db.createRole({role : 'readWriteSystem', privileges: [{resource: { db: '${MONGO_INITDB_DATABASE}', collection: 'system.indexes' }, actions: [ 'changeStream', 'collStats', 'convertToCapped', 'createCollection', 'createIndex', 'dbHash', 'dbStats', 'dropCollection', 'dropIndex', 'emptycapped', 'find', 'insert', 'killCursors', 'listCollections', 'listIndexes', 'planCacheRead', 'remove', 'renameCollectionSameDB', 'update' ]}], roles:[]});\
        db.createUser({ user: '${MONGO_INITDB_ROOT_USERNAME}', pwd: '${MONGO_INITDB_ROOT_PASSWORD}', roles: [{ role: 'dbOwner', db: '${MONGO_INITDB_DATABASE}' }, { role: 'readWriteSystem', db: '${MONGO_INITDB_DATABASE}' }] });" > /docker-entrypoint-initdb.d/initializedb.js

ADD ./docker-files/config/mongod.conf .

CMD ["mongod","-f", "mongod.conf"]

#ENTRYPOINT ["/usr/bin/mongod"]

#Porta padrão
EXPOSE 27017
