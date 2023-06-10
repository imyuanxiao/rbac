import {FormattedMessage} from "@@/exports";
import React from "react";

export const roleEnum = {
    1: {
      text: (
        <FormattedMessage
          id="pages.searchTable.role.admin"
      defaultMessage="管理员"
      />
  ),
  },
  2: {
    text: (
      <FormattedMessage
        id="pages.searchTable.role.user"
    defaultMessage="用户"
      />
  ),
  },
}

export const orgEnum = {
  1:  {
    text: (
      <FormattedMessage
        id="pages.searchTable.org.org1"
        defaultMessage="总公司"
      />
    ),
  },
  2: {
    text: (
      <FormattedMessage
        id="pages.searchTable.org.org2"
        defaultMessage="分公司1"
      />
    ),
  },
  3: {
    text: (
      <FormattedMessage
        id="pages.searchTable.org.org3"
        defaultMessage="分公司2"
      />
    ),
  },
}

export const userStatusEnum = {
    0: {
      text: (
        <FormattedMessage
          id="pages.searchTable.nameStatus.inUse"
          defaultMessage="正常"
        />
      ),
      status: 'Success',
    },
    1: {
      text: (
        <FormattedMessage
          id="pages.searchTable.nameStatus.disabled"
          defaultMessage="停用" />
      ),
      status: 'Error',
    },
    2: {
      text: (
        <FormattedMessage id="pages.searchTable.nameStatus.deleted"
                          defaultMessage="注销" />
      ),
      status: 'Default',
    },
}

export const userStatusOptions = [
  {
    value: 0,
    label: (
      <FormattedMessage
        id="pages.searchTable.nameStatus.inUse"
        defaultMessage="正常"
      />
    ),
  },
  {
    value: 1,
    label: (
      <FormattedMessage
        id="pages.searchTable.nameStatus.disabled"
        defaultMessage="停用"
      />
    ),
  },
  {
    value: 2,
    label: (
      <FormattedMessage
        id="pages.searchTable.nameStatus.deleted"
        defaultMessage="注销"
      />
    ),
  },
];
