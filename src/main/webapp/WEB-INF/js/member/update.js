'use strict'

$(() => {

    $("#inputGroupFileAddon04").click(() => {
        const fileInput = $('#uploadProfile')[0];
        const file = fileInput.files[0]
        const formData = new FormData()
        formData.append('file', file)

        $.ajax({
            url: '/member/upload',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: data => console.log(data),
            error: error => console.log(error)
        })
    })
})