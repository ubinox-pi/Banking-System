FROM node:21-alpine
LABEL authors="Ashish Kushwaha"

WORKDIR /app

COPY package*.json ./
RUN npm ci --only=production

COPY . .

EXPOSE 3000

CMD ["node", "index.js"]
