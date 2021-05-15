import dayjs from 'dayjs';
import { ICapture } from 'app/shared/model/capture.model';
import { DeviceStatus } from 'app/shared/model/enumerations/device-status.model';
import { OS } from 'app/shared/model/enumerations/os.model';

export interface IDevice {
  id?: number;
  type?: string | null;
  blacklisted?: boolean | null;
  hash?: string | null;
  name?: string | null;
  linkDate?: string | null;
  blacklistDate?: string | null;
  blacklistReason?: string | null;
  imei?: string | null;
  cameraPermGranted?: string | null;
  locationPermGranted?: string | null;
  bgInternetPermGranted?: string | null;
  status?: DeviceStatus | null;
  os?: OS | null;
  capture?: ICapture | null;
}

export const defaultValue: Readonly<IDevice> = {
  blacklisted: false,
};
