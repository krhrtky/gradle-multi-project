
rootProject.name = "gradle-multi-project"

include(
    "backend",
    "backend:api",
    "backend:domains",
    "backend:infrastructure",
)
