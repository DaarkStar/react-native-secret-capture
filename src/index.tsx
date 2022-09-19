import { NativeModules } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-secret-capture' doesn't seem to be linked. Make sure: \n\n` +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo managed workflow\n';

const SecretCapture = NativeModules.SecretCapture  ? NativeModules.SecretCapture  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function RNSecretCapture() {
  return SecretCapture;
}
