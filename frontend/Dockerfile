# Stage 1: Build an Angular Docker Image
FROM node:22-alpine AS builder
WORKDIR /app
COPY package*.json /app/
RUN npm install
COPY . /app
ARG configuration=production
RUN npm run build 

# Stage 2: Use the compiled app, ready for production with Nginx
FROM nginx
COPY --from=builder /app/dist/microservices-shop-frontend/browser /usr/share/nginx/html
COPY /nginx-custom.conf /etc/nginx/conf.d/default.conf