import React from 'react';

const Input = ({ name, label,error, ...rest }) => {
    return (
        <div className="form-outline mb-4">
            <input  {...rest} name={ name }
                className="form-control" placeholder={ label } />
            {error && <span style={{color:'red'}}>{error}</span>}
        </div>
    );
};

export default Input;