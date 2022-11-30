FROM node:latest
WORKDIR /src/app
COPY gate-simulator .
RUN npm install
CMD ["npm", "start"]
EXPOSE 9999