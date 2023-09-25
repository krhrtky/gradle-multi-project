
rootProject.name = "gradle-multi-project"

include(
    "backend",
    "backend:api",
    "backend:applications",
    "backend:domains",
    "backend:infrastructure",
)
