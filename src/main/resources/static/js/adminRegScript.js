document.querySelector('form').addEventListener('submit', function (e) {
    e.preventDefault()
    
    // Serialize the form data as JSON
    const formData = new FormData(e.target);
    const jsonData = {};
    formData.forEach((value, key) => {
      jsonData[key] = value;
    })

    //console.log(JSON.stringify(jsonData))
    // Send the data as JSON
    fetch('http://localhost:8080/register/firstAdmin', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(jsonData),
    })
    .then(response => response.json())
    .then(data => {
      // Handle the response
      console.log(data)
    })
  })
