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
const base64 = await RNSecretCapture.captureImage();
```

## Example

```js
import { PermissionsAndroid } from "react-native-secret-capture";
import { RNSecretCapture } from "react-native-secret-capture";

// ...
const onclick = async () => {
    //Ask Permission
    if (Platform.OS === 'android') {
      try {
        const grants = await PermissionsAndroid.requestMultiple([
          PermissionsAndroid.PERMISSIONS.CAMERA,
          PermissionsAndroid.PERMISSIONS.READ_EXTERNAL_STORAGE,
          PermissionsAndroid.PERMISSIONS.WRITE_EXTERNAL_STORAGE,
        ]);
        if (
          grants['android.permission.CAMERA'] ===
          PermissionsAndroid.RESULTS.GRANTED && 
          grants['android.permission.WRITE_EXTERNAL_STORAGE'] ===
          PermissionsAndroid.RESULTS.GRANTED &&
          grants['android.permission.READ_EXTERNAL_STORAGE'] ===
          PermissionsAndroid.RESULTS.GRANTED
        ) {
          const base64 = await RNSecretCapture.captureImage();
          res && console.log("base64", base64);
        } else {
          console.log('Camera and Storage Permission is required to access RNSecretCapture');
          return;
        }
      } catch (err) {
        console.warn(err);
        return;
      }
    }
  };
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
