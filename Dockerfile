FROM nginx:alpine

RUN rm /etc/nginx/conf.d/default.conf
COPY moneyflow-infra-ymls/nginx/nginx.conf /etc/nginx/conf.d/app.conf

EXPOSE 80 443