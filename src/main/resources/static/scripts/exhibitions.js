document.addEventListener("DOMContentLoaded", function () {
    // Hämta data från backend
    fetch('/api/exhibitions/all')
        .then(response => response.json())
        .then(data => {
            // Hitta båda containrarna i HTML
            const activeContainer = document.getElementById('active-exhibitions-container');
            const previousContainer = document.getElementById('previous-exhibitions-container');

            // Loopa igenom varje utställning
            data.forEach(exhibition => {
                // Skapa ett kort (div) för varje utställning
                const card = document.createElement('div');
                card.classList.add('exhibition-card');

                // Fyll kortet med information från databasen
                card.innerHTML = `
                    <img src="${exhibition.imageURL}" alt="${exhibition.name}" class="exhibition-image">
                    <h2>${exhibition.name}</h2>
                    <h3>Av: ${exhibition.artist}</h3>
                    <p><strong>Start:</strong> ${exhibition.startDate} | <strong>Slut:</strong> ${exhibition.endDate}</p>
                    <p>${exhibition.description}</p>
                `;

                // Kolla om den är aktiv eller gammal
                if (exhibition.active) {
                    // Lägg i "Aktuella utställningar"
                    activeContainer.appendChild(card);
                } else {
                    // Lägg i "Tidigare utställningar"
                    previousContainer.appendChild(card);
                }
            });
        })
        .catch(error => console.error('Fel vid hämtning av utställningar:', error));
});