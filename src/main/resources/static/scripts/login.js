document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("loginForm");
  
    form.addEventListener("submit", async (e) => {
      e.preventDefault(); // Hindra vanlig formulärsubmit
  
      const formData = new FormData(form);
      const data = Object.fromEntries(formData.entries());
  
      try {
        const response = await fetch("/api/users/login", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(data)
        });
  
        if (response.ok) {
          // Inloggning lyckades
          window.location.href = "home.html";
        } else {
          const errorMsg = await response.text();
          alert("Inloggning misslyckades: " + errorMsg);
        }
      } catch (error) {
        alert("Fel vid inloggning: " + error.message);
      }
    });
  });
  