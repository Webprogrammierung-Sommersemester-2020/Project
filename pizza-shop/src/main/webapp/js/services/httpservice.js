export default class HttpService {
    constructor() {
    }

    static doRequest(url, method, headers = null, data = null) {
        return new Promise(((resolve, reject) => {
            const httpRequest = new XMLHttpRequest();
            httpRequest.open(method.toUpperCase(), url);
            if (headers) {
                for (let key in headers) {
                    httpRequest.setRequestHeader(key, headers[key])
                }
            }
            if ('GET' === method.toUpperCase()) {
                httpRequest.onload = () => {
                    if ((httpRequest.status === 200) && (httpRequest.readyState === httpRequest.DONE))
                        resolve(httpRequest.responseText);
                };
            } else {
                httpRequest.onload = () => {
                    resolve(httpRequest.status);
                };
            }
            httpRequest.onerror = () => {
                reject(new Error(httpRequest.status + ": " + httpRequest.errorDetail))
            }
            httpRequest.send(data)
        }))
    }

}