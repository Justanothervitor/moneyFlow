FROM docker.io/node:23-alpine AS build
WORKDIR /app
COPY . .
RUN npm install && npm ci
COPY . .
RUN npm run build -- --configuration production

FROM docker.io/nginx:alpine
RUN rm /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist/web-front/browser /usr/share/nginx/html
COPY app-init.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
