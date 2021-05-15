import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITag, defaultValue } from 'app/shared/model/tag.model';

export const ACTION_TYPES = {
  SEARCH_TAGS: 'tag/SEARCH_TAGS',
  FETCH_TAG_LIST: 'tag/FETCH_TAG_LIST',
  FETCH_TAG: 'tag/FETCH_TAG',
  CREATE_TAG: 'tag/CREATE_TAG',
  UPDATE_TAG: 'tag/UPDATE_TAG',
  PARTIAL_UPDATE_TAG: 'tag/PARTIAL_UPDATE_TAG',
  DELETE_TAG: 'tag/DELETE_TAG',
  RESET: 'tag/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITag>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type TagState = Readonly<typeof initialState>;

// Reducer

export default (state: TagState = initialState, action): TagState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_TAGS):
    case REQUEST(ACTION_TYPES.FETCH_TAG_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TAG):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_TAG):
    case REQUEST(ACTION_TYPES.UPDATE_TAG):
    case REQUEST(ACTION_TYPES.DELETE_TAG):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_TAG):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_TAGS):
    case FAILURE(ACTION_TYPES.FETCH_TAG_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TAG):
    case FAILURE(ACTION_TYPES.CREATE_TAG):
    case FAILURE(ACTION_TYPES.UPDATE_TAG):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_TAG):
    case FAILURE(ACTION_TYPES.DELETE_TAG):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_TAGS):
    case SUCCESS(ACTION_TYPES.FETCH_TAG_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TAG):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_TAG):
    case SUCCESS(ACTION_TYPES.UPDATE_TAG):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_TAG):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_TAG):
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

const apiUrl = 'api/tags';
const apiSearchUrl = 'api/_search/tags';

// Actions

export const getSearchEntities: ICrudSearchAction<ITag> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_TAGS,
  payload: axios.get<ITag>(`${apiSearchUrl}?query=${query}`),
});

export const getEntities: ICrudGetAllAction<ITag> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TAG_LIST,
  payload: axios.get<ITag>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ITag> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TAG,
    payload: axios.get<ITag>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ITag> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TAG,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITag> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TAG,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ITag> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_TAG,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITag> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TAG,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
