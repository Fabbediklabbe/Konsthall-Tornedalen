
// Vänta tills hela sidan är laddad innan JS körs
document.addEventListener("DOMContentLoaded", () =>
{
    // SKicka en GET-förfrågan till backend för att hämta informationen om den inloggade användaren
    fetch("/api/users/info")
        .then(response =>
        {
        // Kontrollerar om svaret är OK (HTTP 200)
            if (!response.ok)
            {
                // Om inte, kasta ett fel så det fångas i catch-blocket
                throw new Error("Kunde inte hämta användarinformation");
            }
            // Om allt är OK returnera svaret som JSON
            return response.json();
        })

        .then(data =>
        {
        // Använd datan från svaret för att fylla i HTML-elementet på sidan

            // Fyll i HTML-elementen med användarens data
            document.getElementById("firstName").textContent = data.name;
            document.getElementById("lastName").textContent = data.lastName;
            document.getElementById("email").textContent = data.email;
        })
        .catch(error =>
        {
            console.error("Fel vid hämtning av användardata:", error);
        });

    fetch("/api/users/comments")
        .then(response => {
            if (!response.ok) throw new Error("Kunde inte hämta kommentarer");
            return response.json();
        })
        .then(comments => {
            const commentList = document.getElementById("commentList");
            commentList.innerHTML = ""; // Töm listan först

            if (comments.length === 0) {
                commentList.innerHTML = "<li>Inga kommentarer än.</li>";
                return;
            }

            comments.forEach(c => {
                const li = document.createElement("li");
                const date = new Date(c.createdAt);
                li.textContent = `"${c.comment}" – ${date.toLocaleString("sv-SE")}`;
                commentList.appendChild(li);
            });
        })
        .catch(error => {
            console.error("Fel vid hämtning av kommentarer:", error);
        });
});
