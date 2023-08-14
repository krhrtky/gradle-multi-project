import { beforeAll, afterAll, afterEach } from "vitest";
import { setupServer } from "msw/node";
import { getOpenAPIDefinitionMSW } from "../libs/api/generated/openAPIDefinition.ts";
import Axios from "axios";

const server = setupServer(...getOpenAPIDefinitionMSW());

Axios.defaults.baseURL = "http://localhost:5173/api";

beforeAll(() => server.listen({onUnhandledRequest: "error" }))
afterEach(() => server.resetHandlers());
afterAll(() => server.close());
