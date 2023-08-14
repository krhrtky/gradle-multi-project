import { describe, it, expect, afterEach } from "vitest";
import { render, cleanup } from "@testing-library/react";
import App from "./App";

describe(App.name, () => {
    afterEach(cleanup);
    it("first render", async () => {
        const { container } = render( <App/>);
        expect(container.innerHTML).toMatchSnapshot();
    });
});
