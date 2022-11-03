import React from "react";

const TextArea = ({ name, label, error, ...rest }) => {
    return (
        <React.Fragment>
            <textarea
                {...rest}
                className="form-control validate"
                rows="3"
                name={ name }
                label={ label }
                required
            ></textarea>
            { error && <div className="alert alert-danger">{ error }</div> }
        </React.Fragment>
    )
}
export default TextArea