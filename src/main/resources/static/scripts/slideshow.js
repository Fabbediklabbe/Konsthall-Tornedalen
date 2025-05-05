// JavaScript för Bildspel

//Håller koll på vilken vild som visas
let slideIndex = 0;
//Variabler för bilderna, prickarna och timer-intervallet
let slides, dots, timer;

//Initierar bildspelet när sidan laddas
function initSlides() {

  //Hämtar bild-element
  slides = document.querySelectorAll('.slide');
  //Hämtar alla prickar
  dots = document.querySelectorAll('.dot');
  //Visar första bilden
  showSlide(slideIndex);
  // Växla bild automatiskt var 5:e sekund
  timer = setInterval(nextSlide, 5000);
}

// Visar en viss bild och uppdaterar prickarnas aktiva status
function showSlide(index) {
  slides.forEach((s, i) => {
    //Endast en slide för klassen "active"
    s.classList.toggle('active', i === index);
  });
  dots.forEach((d, i) => {
    // Endast en prick för klassen "active"
    d.classList.toggle('active', i === index);
  });
}

// Går till nästa bild (används av timern)
function nextSlide() {
  // Gå till nästa, loopa runt i slutet
  slideIndex = (slideIndex + 1) % slides.length;
  showSlide(slideIndex);
}

// Flyttar n bilder framåt eller bakåt
function plusSlides(n) {
  slideIndex = (slideIndex + n + slides.length) % slides.length;
  showSlide(slideIndex);
  //Startar om timern vid manuell växling
  resetTimer();
}

//Visar en specifik bild
function currentSlide(n) {
  slideIndex = n - 1;
  showSlide(slideIndex);
  resetTimer();
}
// Startar om den automatiska bildväxlingen
function resetTimer() {
  clearInterval(timer);
  timer = setInterval(nextSlide, 5000);
}

// Kör initiering när hela DOM-trädet laddats.
document.addEventListener('DOMContentLoaded', initSlides);