/**
 * @see https://umijs.org/zh-CN/plugins/plugin-access
 * */
export default function access(initialState: { currentUser?: API.CurrentUser } | undefined) {
  const { currentUser } = initialState ?? {};

  const permissionId = {

    // 需要操作权限的页面
    // 获得页面权限最低需要的操作权限
    canUserPage: 1006,
    canLoginHistoryPage: 1007,

    // 其他操作权限
    canAddUser: 1001,
    canDeleteUser: 1002,
    canEditUser: 1003,

    canRolePage: 3004,

    canAddRole: 3001,
    canDeleteRole: 3002,
    canEditRole: 3003,

    // 不需要操作权限的页面
    canAdminData: 5000,

  }

  const hasPermission = (id: any) => {
    return (currentUser && currentUser.permissionIds?.includes(id)) || false;
  };

  return {

    canAdminUser:
      hasPermission(permissionId.canUserPage) ||
      hasPermission(permissionId.canLoginHistoryPage),
    canUserPage: hasPermission(permissionId.canUserPage),
    canLoginHistoryPage: hasPermission(permissionId.canLoginHistoryPage),

    canAddUser: hasPermission(permissionId.canAddUser),
    canDeleteUser: hasPermission(permissionId.canDeleteUser),
    canEditUser: hasPermission(permissionId.canEditUser),

    canAdminRole:
     hasPermission(permissionId.canRolePage),
    canRolePage: hasPermission(permissionId.canRolePage),

    canAddRole: hasPermission(permissionId.canAddRole),
    canDeleteRole: hasPermission(permissionId.canDeleteRole),
    canEditRole: hasPermission(permissionId.canEditRole),

    canAdminData: hasPermission(permissionId.canAdminData),

  };
}

