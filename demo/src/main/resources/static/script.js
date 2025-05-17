document.getElementById("supportForm").addEventListener("submit", function (e) {
    e.preventDefault();

    const studentName = document.getElementById("studentName").value;
    const type = document.getElementById("type").value;
    const message = document.getElementById("message").value;

    fetch("/support/add", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            studentName: studentName,
            type: type,
            message: message
        })
    })
    .then(response => {
        if (!response.ok) throw new Error("Failed to submit");
        return response.text();
    })
    .then(msg => {
        alert(msg);
        document.getElementById("supportForm").reset();
        loadSupport(); // ✅ reloads list after adding
    })
    .catch(err => alert("Error: " + err.message));
});

function loadSupport() {
    fetch("/support/all")
        .then(res => res.json())
        .then(data => {
            const list = document.getElementById("supportList");
            list.innerHTML = "";
            data.forEach(s => {
                const li = document.createElement("li");
                li.textContent = `#${s.id} [${s.type}] ${s.studentName}: ${s.message}`;
                list.appendChild(li);
            });
        });
}

// Load support requests on page load
loadSupport();
