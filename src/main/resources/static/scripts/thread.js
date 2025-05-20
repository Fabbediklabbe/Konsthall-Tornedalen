document.addEventListener("DOMContentLoaded", () => {

  //Hämta trådens ID från attributet data-thread-id på <main>-elementet
  const threadId = document.querySelector('.thread-container')?.dataset.threadId;

  //Hämta elementet där utställningsinfo ska visas
  const container = document.getElementById('exhibition-info');

  //Om tråd-ID eller container inte finns, avbryt.
  if (!threadId || !container) return;

  //Hämta tråddata (och kopplad utställning) från backend via API
  fetch(`/api/threads/${threadId}`)

    // Konvertera svaret till JSON
    .then(res => res.json())
    .then(data => {
      const exhibition = data.exhibition;

      //Om ingen utställning hittas, avbryt.
      if (!exhibition) return;

      // Skapa ett <h3>- element för utställningens titel
      const title = document.createElement('h3');
      title.textContent = exhibition.name;

      // Skapa ett <img>-element för utställningens bild
      const img = document.createElement('img');
      img.src = exhibition.imageURL;
      img.alt = exhibition.name;
      img.style.width = "100%";

      //Lägg till titel och bild i rätt container på sidan
      container.appendChild(title);
      container.appendChild(img);
    });
});