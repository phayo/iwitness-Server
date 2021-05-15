import axios from 'axios';
import {
  ICrudSearchAction,
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction,
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICapture, defaultValue } from 'app/shared/model/capture.model';

export const ACTION_TYPES = {
  SEARCH_CAPTURES: 'capture/SEARCH_CAPTURES',
  FETCH_CAPTURE_LIST: 'capture/FETCH_CAPTURE_LIST',
  FETCH_CAPTURE: 'capture/FETCH_CAPTURE',
  CREATE_CAPTURE: 'capture/CREATE_CAPTURE',
  UPDATE_CAPTURE: 'capture/UPDATE_CAPTURE',
  PARTIAL_UPDATE_CAPTURE: 'capture/PARTIAL_UPDATE_CAPTURE',
  DELETE_CAPTURE: 'capture/DELETE_CAPTURE',
  RESET: 'capture/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICapture>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type CaptureState = Readonly<typeof initialState>;

// Reducer

export default (state: CaptureState = initialState, action): CaptureState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_CAPTURES):
    case REQUEST(ACTION_TYPES.FETCH_CAPTURE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CAPTURE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CAPTURE):
    case REQUEST(ACTION_TYPES.UPDATE_CAPTURE):
    case REQUEST(ACTION_TYPES.DELETE_CAPTURE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_CAPTURE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_CAPTURES):
    case FAILURE(ACTION_TYPES.FETCH_CAPTURE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CAPTURE):
    case FAILURE(ACTION_TYPES.CREATE_CAPTURE):
    case FAILURE(ACTION_TYPES.UPDATE_CAPTURE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_CAPTURE):
    case FAILURE(ACTION_TYPES.DELETE_CAPTURE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_CAPTURES):
    case SUCCESS(ACTION_TYPES.FETCH_CAPTURE_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_CAPTURE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CAPTURE):
    case SUCCESS(ACTION_TYPES.UPDATE_CAPTURE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_CAPTURE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CAPTURE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/captures';
const apiSearchUrl = 'api/_search/captures';

// Actions

export const getSearchEntities: ICrudSearchAction<ICapture> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_CAPTURES,
  payload: axios.get<ICapture>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<ICapture> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CAPTURE_LIST,
    payload: axios.get<ICapture>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ICapture> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CAPTURE,
    payload: axios.get<ICapture>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICapture> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CAPTURE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<ICapture> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CAPTURE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ICapture> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_CAPTURE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICapture> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CAPTURE,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
