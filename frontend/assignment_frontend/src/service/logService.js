import Raven from "raven-js";

function init() {
    Raven.config("https://2901f3cb20cb4aa88b42a70b01150f1e@o4504038871334912.ingest.sentry.io/4504038873104384", {
        release: '0.0.0',
        environment: 'development-test',
    }).install();
}

function log(error) {
    Raven.captureException(error);
}

export default {
    init,
    log
};