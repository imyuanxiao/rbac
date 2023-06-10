/**
 * @see https://umijs.org/zh-CN/plugins/plugin-access
 * */
export default function access(initialState: { currentUser?: API.CurrentUser } | undefined) {
  const { currentUser } = initialState ?? {};

  const permissionId = {
    canUserAdmin: 1000,
    canAddUser: 1001,
    canDeleteUser: 1002,
    canEditUser: 1003,
    canDataAdmin: 5000,
    canRoleAdmin: 3000
  }

  return {
    // canAdmin: currentUser && currentUser.roleIds?.includes(1),
    canUserAdmin: (currentUser && currentUser.permissionIds?.includes(permissionId.canUserAdmin)) || false,
    canDataAdmin: (currentUser && currentUser.permissionIds?.includes(permissionId.canDataAdmin)) || false,
    canRoleAdmin: (currentUser && currentUser.permissionIds?.includes(permissionId.canRoleAdmin)) || false,
    canEditUser: (currentUser && currentUser.permissionIds?.includes(permissionId.canEditUser)) || false,
    canAddUser: (currentUser && currentUser.permissionIds?.includes(permissionId.canAddUser)) || false,
    canDeleteUser: (currentUser && (currentUser.permissionIds?.includes(permissionId.canDeleteUser))) || false,
  };
}

