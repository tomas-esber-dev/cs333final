function toggleContent() {
    var selector = document.getElementById("htmlSelector");
    var selectedValue = selector.value;

    // Hide all content divs
    var contentDivs = document.querySelectorAll(".tree");
    contentDivs.forEach(function (div) {
      div.classList.remove("active");
    });

    // Show the selected content div
    var selectedDiv = document.getElementById(selectedValue);
    selectedDiv.classList.add("active");
  }