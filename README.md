# react-native-secret-capture
Capture Image without preview and get base64
## Installation

```sh
npm install react-native-secret-capture
```

## Usage

```js
import { RNSecretCapture } from "react-native-secret-capture";

// ...
Add Permissions then user the code below for the Action
RNSecretCapture.captureImage((base64: string)=> {console.log('Base64', base64)});
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
