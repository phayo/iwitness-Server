import dayjs from 'dayjs';
import { IDevice } from 'app/shared/model/device.model';
import { ITag } from 'app/shared/model/tag.model';
import { CaptureMode } from 'app/shared/model/enumerations/capture-mode.model';

export interface ICapture {
  id?: number;
  duration?: string | null;
  title?: string | null;
  description?: string | null;
  cloudUrl?: string | null;
  recordStartTime?: string | null;
  recordEndTime?: string | null;
  cloudUploadStartTime?: string | null;
  cloudUploadploadEndTime?: string | null;
  serverUploadTime?: string | null;
  publicHash?: string | null;
  latitude?: number | null;
  longitude?: number | null;
  hash?: string | null;
  captureMode?: CaptureMode | null;
  devices?: IDevice[] | null;
  tags?: ITag[] | null;
}

export const defaultValue: Readonly<ICapture> = {};
