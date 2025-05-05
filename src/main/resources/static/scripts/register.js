document.getElementById("registerForm").addEventListener("submit", async function(e) {
    e.preventDefault(); // Förhindra att sidan laddas om
  
    const formData = new FormData(this);
    const data = Object.fromEntries(formData.entries());
  
    const response = await fetch("/api/users/register", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data)
    });
  
    if (response.ok) {
      alert("Registreringen lyckades!");
      window.location.href = "/login";
    } else {
      const text = await response.text();
      alert("Registrering misslyckades: " + text);
    }
  });
  