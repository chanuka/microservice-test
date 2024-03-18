FROM nginx:latest

COPY nginx.conf /etc/nginx/nginx.conf
COPY tls/new-cert/wireme_server.crt /etc/ssl/certs/wireme_server.crt
COPY tls/new-cert/wireme_server.key /etc/ssl/private/wireme_server.key