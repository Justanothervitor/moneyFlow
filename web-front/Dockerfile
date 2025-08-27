FROM node:23-alpine AS build
WORKDIR /app
COPY /web-front .
RUN npm install
RUN npm run build

FROM nginx:alpine
COPY --from=build /app/dist/web-front/browser /usr/share/nginx/static
COPY /web-front/nginx.conf /etc/nginx/nginx.conf
COPY /web-front/mime.types /etc/nginx/mime.types
EXPOSE 80
CMD ["nginx","-g","daemon off;"]
