# FD_TeachingPlatform

复旦大学继续教育学院 教学参考平台

## Getting Started

### Prerequisites

- node >= 16
- java >= 11
- maven >= 3.6.3

### Setup

- frontend
  - Run: `npm run setup`

- backend
  -  create .env file in root directory, and create the following environment variables:
  
    
    ```shell
    CONF_MYSQL_URL=
    CONF_MYSQL_USERNAME=
    CONF_MYSQL_PASSWORD=

    CONF_REDIS_HOST=
    CONF_REDIS_PORT=
    CONF_REDIS_PASSWORD=
    CONF_REDIS_DATABASE=
    ```
    use your own mysql and redis configuration.

    - Run: `mvn clean package`
    - Run: `chmod +x ./scripts/backendStart.sh`
    - Run: `./scripts/backendStart.sh`


## Development

- Please add your github username to the [CODEOWNERS](.github/CODEOWNERS) file if you want to be a code owner of this project folder.
- Please add your email to the [collaborators](collaborators.md) file.
- Please fill in your [job assignment](job_assignment.md) 

### frontend

  - Run: `npm run frontend:dev`

### backend
  - start local debug

