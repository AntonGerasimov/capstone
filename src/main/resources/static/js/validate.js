function validateNames() {
  var firstNameField = document.getElementById("firstName");
  var lastNameField = document.getElementById("lastName");

  var firstName = firstNameField.value;
  var lastName = lastNameField.value;

  // Regular expression to check if the input contains only letters
  var lettersOnlyRegex = /^[A-Za-z]+$/;

  if (!lettersOnlyRegex.test(firstName)) {
    alert("First name must contain only letters.");
    firstNameField.focus();
    return false;
  }

  if (!lettersOnlyRegex.test(lastName)) {
    alert("Last name must contain only letters.");
    lastNameField.focus();
    return false;
  }

  // Both fields contain only letters
  return true;
}