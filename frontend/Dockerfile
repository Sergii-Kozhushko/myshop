# variant 1 - how to make angular image, very slow
#FROM node:alpine
#
#WORKDIR /app
#
#EXPOSE 4200
#
#COPY package.json ./
#
#RUN npm install
#
#COPY . .
#
#CMD ["npm","run"]

# variant 2
FROM nginx:latest

COPY ./dist/frontend-angular /usr/share/nginx/html
COPY ./dist/frontend-angular/assets/ssl /etc/ssl
COPY ./nginx-config.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
