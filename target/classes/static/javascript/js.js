// Change style of navBar on scroll
window.onscroll = function() {myFunction()};
function myFunction() {
    var navBar = document.getElementById("myNavBar");
    if (document.body.scrollTop > 100 || document.documentElement.scrollTop > 100) {
        navBar.className = "w3-bar" + " w3-card" + " w3-animate-top" + " w3-white";
    } else {
        navBar.className = navBar.className.replace(" w3-card w3-animate-top w3-white", "");
    }
}


