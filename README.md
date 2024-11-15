docker pull public.ecr.aws/docker/library/postgres:latest                                                                                                                                      
docker run \
  --name some-postgres \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=interview \
  -p 5432:5432 \
  -d \
  public.ecr.aws/docker/library/postgres:latest
