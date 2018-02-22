if (window.console) {
  console.log("Welcome to your Play application's JavaScript!");

  document.getElementById("myButton").addEventListener("click", clickHandler);

  function clickHandler(){
    window.location.href = "http://165.227.94.251:9000/alerts"
  }
}
