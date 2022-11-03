import Joi from 'joi-browser';
import React, { Component } from 'react';
import Input from './input';
import Select from './select';
import TextArea from './textArea';
class Form extends Component {
  state = {
    data: {},
    errors: []
  }
  validate = () => {
    const options = { abortEarly: false };
    const { error } = Joi.validate(this.state.data, this.schema, options);
    if (!error) return null;

    const errors = {};
    for (let item of error.details) errors[item.path[0]] = item.message;
    return errors;
  };

  validateProperty = ({ name, value }) => {
    const obj = { [name]: value };
    const schema = { [name]: this.schema[name] };
    const { error } = Joi.validate(obj, schema);
    return error ? error.details[0].message : null;
  };

  handleSubmit = e => {
    console.log("handleSubmi");

    e.preventDefault();

    const errors = this.validate();
    console.log(errors);
    this.setState({ errors: errors || {} });
    if (errors) return;
    this.doSubmit();
  };

  handleChange = ({ currentTarget: input }) => {
    const errors = { ...this.state.errors };
    const errorMessage = this.validateProperty(input);
    if (errorMessage) errors[input.name] = errorMessage;
    else delete errors[input.name];

    const data = { ...this.state.data };
    data[input.name] = input.value;

    this.setState({ data, errors });
  };

  renderButton(label,className="btn btn-primary btn-block fa-lg gradient-custom-2 mb-3",style) {
    return (
      <button class={className}
        style={{style}}
          >{ label }</button>
    );
  }
  renderTextArea(name, label) {
    const { data, errors } = this.state;
    console.log("Des: "+data.des);
    return(
      <TextArea 
      name={name} 
      value={data[name]}
      label={label}
      onChange={ this.handleChange }
      error={ errors[name] }/>
    )
  }

  renderSelect(name, label, options) {
    const { data, errors } = this.state;
    console.log("Optional Data: " + options);

    return (
      <Select
        name={ name }
        value={ data[name] }
        label={ label }
        options={ options }
        onChange={ this.handleChange }
        error={ errors[name] }
      />
    );
  }

  renderInput(name, label, type = "text", className = "") {
    const { data, errors } = this.state;

    return (
      <Input
        type={ type }
        name={ name }
        value={ data[name] }
        label={ label }
        className={ className }
        onChange={ this.handleChange }
        error={ errors[name] }
      />
    );
  }
}

export default Form;