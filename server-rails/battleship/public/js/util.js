// from https://stackoverflow.com/a/30810322/5549727
function copyTextToClipboard(text) {
    var textArea = document.createElement("textarea");

    textArea.style.position = 'fixed';
    textArea.style.top = 0;
    textArea.style.left = 0;

    textArea.style.width = '2em';
    textArea.style.height = '2em';

    textArea.style.padding = 0;

    textArea.style.border = 'none';
    textArea.style.outline = 'none';
    textArea.style.boxShadow = 'none';

    textArea.style.background = 'transparent';

    textArea.value = text;

    document.body.appendChild(textArea);

    textArea.select();

    var successful = false;

    try {
        successful = document.execCommand('copy');
    } catch (err) {}

    document.body.removeChild(textArea);

    return successful;
}

// from https://stackoverflow.com/a/6234804/5549727
function escapeHtml(unsafe) {
    return unsafe
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}

function ajax(type, url, data, success, error) {
    var json = {url: url, type: type, data: data || {}};
    json.data.authenticity_token = $('meta[name="csrf-token"]').attr('content');

    if (success) {
        json.success = success;
    }

    if (error) {
        json.error = error;
    }

    $.ajax(json);
}

function get(url, params) {
    ajax('GET', url, params.data, params.success, params.error);
}

function post(url, params) {
    ajax('POST', url, params.data, params.success, params.error);
}