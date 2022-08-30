### STAGE 1: Build ###
FROM node:16.13-alpine AS build
WORKDIR /usr/src/app
COPY ./client-app/package.json ./client-app/package-lock.json ./
RUN npm install
COPY ./client-app .
RUN npm run build

### STAGE 2: Run ###
FROM nginx:1.13.1-alpine
COPY ./client-app/nginx.conf /etc/nginx/nginx.conf
COPY --from=build /usr/src/app/dist/client-app /usr/share/nginx/html
