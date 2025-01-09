container-up:
	docker compose -p member-local --project-directory . -f src/main/resources/db/container/docker-compose.yaml up -d

container-down:
	docker compose -p member-local down --remove-orphans --volumes

container-logs:
	docker compose -p member-local logs -f