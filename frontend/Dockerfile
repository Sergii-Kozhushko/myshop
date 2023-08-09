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

COPY ./dist/frontend-angular /usr/share/ngnix/html

EXPOSE 4200
