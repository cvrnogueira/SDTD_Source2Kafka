# --- Begin BUILD stage ---

FROM openjdk AS builder

WORKDIR /home/app

COPY . .

RUN ./sbtx assembly

# --- End BUILD stage ---

# --- Begin Deploy stage ---

FROM openjdk

WORKDIR /home/app

COPY --from=builder /home/app/target/scala*/*-assembly*.jar ./app.jar

CMD java -jar app.jar

# --- End Deploy stage ---