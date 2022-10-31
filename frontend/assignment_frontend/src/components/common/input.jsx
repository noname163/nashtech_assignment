import React from 'react';

const Input = ({ name, label,error, ...rest }) => {
    return (
        <div class="form-outline mb-4">
            <input  {...rest} name={ name }
                class="form-control" placeholder={ label } />
            {error && <span style={{color:'red'}}>{error}</span>}
        </div>
    );
};

export default Input;