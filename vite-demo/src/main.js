import styles from './style.module.scss'
import javascriptLogo from '@/javascript.svg'
import viteLogo from '/vite.svg'
import { setupCounter } from './counter.ts'
import imageSrc from '@/image-src.svg'
import { v4 as uuidv4 } from 'uuid';

document.querySelector('#app').innerHTML = `
  <div>
    <a href="https://vite.dev" target="_blank">
      <img src="${viteLogo}" class="${styles.logo}" alt="Vite logo" />
    </a>
    <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript" target="_blank">
      <img src="${javascriptLogo}" class="${styles.logo} ${styles.vanilla}" alt="JavaScript logo" />
    </a>
    <h1>${import.meta.env.VITE_APP_TITLE}</h1>
    <div class="${styles.card}">
      <button id="counter" type="button"></button>
    </div>
    <p class="${styles.readTheDocs}">
      Click on the Vite logo to learn more
    </p>
    <img src="${imageSrc}" alt="Image from src folder" />
    <button id="load-lazy-module">Load dynamic module</button>
    <p style="margin-top: 10px; font-size: 0.9em; color: #666;">Generated UUID for demo: <strong>${uuidv4()}</strong></p>
  </div>
`

setupCounter(document.querySelector('#counter'))

document.getElementById('load-lazy-module').addEventListener('click', async () => {
  const { showLazyMessage } = await import('./lazy-module.js');
  showLazyMessage();
});
