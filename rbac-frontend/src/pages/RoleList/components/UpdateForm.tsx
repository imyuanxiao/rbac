import {
  ProForm,
  ProFormText, ProFormTextArea, ProFormTreeSelect,
} from '@ant-design/pro-components';
import { FormattedMessage, useIntl } from '@umijs/max';
import {Modal} from 'antd';
import React from 'react';
import { permissionTreeEnum } from "@/utils/comonValue";

export type FormValueType = {
  target?: string;
  template?: string;
  type?: string;
  time?: string;
  frequency?: string;
} & Partial<API.RoleListItem>;

export type UpdateFormProps = {
  onCancel: (flag?: boolean, formVals?: FormValueType) => void;
  onSubmit: (values: FormValueType) => Promise<void>;
  updateModalOpen: boolean;
  values: Partial<API.RoleListItem>;
};

const UpdateForm: React.FC<UpdateFormProps> = (props) => {
  const intl = useIntl();
  return (
    <Modal
      width={640}
      bodyStyle={{ padding: '32px 40px 48px' }}
      destroyOnClose
      title={intl.formatMessage({
        id: 'pages.searchTable.updateForm.edit',
        defaultMessage: '编辑',
      })}
      open={props.updateModalOpen}
      onCancel={() => {
        props.onCancel();
      }}
      footer={null}
    >
      <ProForm
        onFinish={props.onSubmit}
        initialValues={{
          id: props.values.id,
          roleName: props.values.roleName,
          description: props.values.description,
          permissionIds: props.values.permissionIds,
        }}
      >
          <ProFormText
            name="id"
            label="ID"
            hidden
            disabled
          />
          <ProFormText
            name="roleName"
            label={intl.formatMessage({
              id: 'pages.searchTable.updateForm.roleName.nameLabel',
              defaultMessage: '用户名',
            })}
            width="md"
            rules={[
              {
                required: true,
                message: (
                  <FormattedMessage
                    id="pages.searchTable.updateForm.roleName.nameRules"
                    defaultMessage="请输入角色名！"
                  />
                ),
              },
            ]}
          />
        <ProFormTextArea
          name="description"
          width="md"
          label={intl.formatMessage({
            id: 'pages.searchTable.updateForm.roleProps.description',
            defaultMessage: '描述',
          })}
        />
        <ProFormTreeSelect
          name="permissionIds"
          placeholder="Please select"
          allowClear
          width={330}
          secondary
          label={intl.formatMessage({
            id: 'pages.searchTable.updateForm.roleProps.permissionIds',
            defaultMessage: '权限',
          })}
          request={async () => {
            return permissionTreeEnum;
          }}
          // tree-select args
          fieldProps={{
            showArrow: false,
            filterTreeNode: true,
            showSearch: true,
            popupMatchSelectWidth: false,
            labelInValue: true,
            autoClearSearchValue: true,
            multiple: true,
            treeNodeFilterProp: 'title',
            fieldNames: {
              label: 'title',
            },
            treeCheckable: true,
          }}
        />
      </ProForm>
    </Modal>
  );
};

export default UpdateForm;
