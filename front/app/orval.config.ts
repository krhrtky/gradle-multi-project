import { defineConfig } from "orval";

export default defineConfig({
    app: {
        input: "../../openapi.json",
        output: {
            client: "react-query",
            target: "./src/libs/api/generated",
            mock: true,
        },
    },
});
