import { ICapture } from 'app/shared/model/capture.model';

export interface ITag {
  id?: number;
  tagName?: string | null;
  capture?: ICapture | null;
}

export const defaultValue: Readonly<ITag> = {};
