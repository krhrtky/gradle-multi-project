import {Suspense, useState} from 'react'
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import { useList } from "./libs/api";

const client = new QueryClient();

function App() {
  const [count, setCount] = useState(0)

  return (
    <QueryClientProvider client={client}>
      <div>
        <a href="https://vitejs.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      <h1>Vite + React</h1>
      <Component />
      <div className="card">
        <button onClick={() => setCount((count) => count + 1)}>
          count is {count}
        </button>
        <p>
          Edit <code>src/App.tsx</code> and save to test HMR
        </p>
      </div>
      <p className="read-the-docs">
        Click on the Vite and React logos to learn more
      </p>
    </QueryClientProvider>
  )
}

const Component = function () {

    const query = useList();

    return (
        <Suspense fallback={<div>suspended</div>}>
            <div>
                {JSON.stringify(query.data?.data)}
            </div>
        </Suspense>
    );
}

export default App
