import './style.scss'
import javascriptLogo from '@/javascript.svg'
import viteLogo from '/vite.svg'
import { setupCounter } from './counter.ts'
import imageSrc from '@/image-src.svg'

document.querySelector('#app').innerHTML = `
  <div>
    <a href="https://vite.dev" target="_blank">
      <img src="${viteLogo}" class="logo" alt="Vite logo" />
    </a>
    <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript" target="_blank">
      <img src="${javascriptLogo}" class="logo vanilla" alt="JavaScript logo" />
    </a>
    <h1>${import.meta.env.VITE_APP_TITLE}</h1>
    <div class="card">
      <button id="counter" type="button"></button>
    </div>
    <p class="read-the-docs">
      Click on the Vite logo to learn more
    </p>
    <img src="${imageSrc}" alt="Image from src folder" />
    <button id="load-lazy-module">Load dynamic module</button>
  </div>
`

setupCounter(document.querySelector('#counter'))

document.getElementById('load-lazy-module').addEventListener('click', async () => {
  const { showLazyMessage } = await import('./lazy-module.js');
  showLazyMessage();
});
